# -*- coding: utf-8 -*-
import cv2
import numpy as np
import os.path as path
import pymysql
import time
import uuid
import sys
import redis

print(sys.argv)
monitor_url = "F:\\Graduation_Desgin\\code\\web-boot\\collection-python\\src\\main\\resources\\static\\video" \
              "\\20231214_161219.mp4"
user_id = " "
redisKey = ""
if len(sys.argv) >= 2:
    monitor_url = sys.argv[1]
    user_id = sys.argv[2]
    redisKey = sys.argv[3]


def center(points):
    """计算给定矩阵的质心"""
    x = (points[0][0] + points[1][0] + points[2][0] + points[3][0]) / 4
    y = (points[0][1] + points[1][1] + points[2][1] + points[3][1]) / 4
    return np.array([np.float32(x), np.float32(y)], np.float32)


font = cv2.FONT_HERSHEY_SIMPLEX


class Pedestrian():
    """Pedestrian（行人）
    每个行人都由ROI，ID和卡尔曼过滤器组成，因此我们创建了一个步行者类来保存对象状态
    """

    def __init__(self, id, frame, track_window):
        """使用跟踪窗口坐标初始化行人对象"""
        # 设置ROI区域
        self.id = int(id)
        x, y, w, h = track_window
        self.track_window = track_window
        self.roi = cv2.cvtColor(frame[y:y + h, x:x + w], cv2.COLOR_BGR2HSV)
        roi_hist = cv2.calcHist([self.roi], [0], None, [16], [0, 180])
        self.roi_hist = cv2.normalize(roi_hist, roi_hist, 0, 255, cv2.NORM_MINMAX)

        # 设置卡尔曼滤波器
        self.kalman = cv2.KalmanFilter(4, 2)
        self.kalman.measurementMatrix = np.array([[1, 0, 0, 0], [0, 1, 0, 0]], np.float32)
        self.kalman.transitionMatrix = np.array([[1, 0, 1, 0], [0, 1, 0, 1], [0, 0, 1, 0], [0, 0, 0, 1]], np.float32)
        self.kalman.processNoiseCov = np.array([[1, 0, 0, 0], [0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]],
                                               np.float32) * 0.03
        self.measurement = np.array((2, 1), np.float32)
        self.prediction = np.zeros((2, 1), np.float32)
        self.term_crit = (cv2.TERM_CRITERIA_EPS | cv2.TERM_CRITERIA_COUNT, 10, 1)
        self.center = None
        # self.update(frame)

    # def __del__(self):
    # print("Pedestrian %d destroyed" % self.id)


# 建立数据库连接
conn = pymysql.connect(
    host='47.98.228.176',  # 主机名（或IP地址）
    port=3307,  # 端口号，默认为3306
    user='root',  # 用户名
    password='xqQsqDeNAMuSEwu',  # 密码
    charset='utf8mb4'  # 设置字符编码
)

# 获取mysql服务信息（测试连接，会输出MySQL版本号）
print("mysql连接成功，版本:" + conn.get_server_info())

cursor = conn.cursor()

# 选择数据库
conn.select_db("magic_tool_for_social_phobia")

startTime = time.perf_counter()
sqlStartTime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
print("开始计时：" + str(startTime))


def insertSQL(people_num, sql_end_time):
    collect_begin_time = sqlStartTime
    collect_end_time = sql_end_time
    traffic = people_num
    create_by = "python camera"
    create_time = sqlStartTime
    print(uuid.uuid1())
    # 执行查询操作
    cursor.execute(
        'INSERT INTO i_traffic(id,collect_begin_time,collect_end_time,traffic,create_by,create_time,user_id,monitor_id) VALUES(%s,%s,%s,%s,%s,%s,%s,%s)',
        [uuid.uuid1(), collect_begin_time, collect_end_time, traffic, create_by, create_time, user_id,monitor_url])
    conn.commit()
    # 获取查询结果，返回元组
    # result: tuple = cursor.fetchall()
    # print(result)


def main(*args):
    print(*args)
    camera = cv2.VideoCapture(path.join(path.dirname(__file__), monitor_url))  # 加载视频
    people_num = 0
    # camera = cv2.VideoCapture(0) #网络摄像头
    history = 20  # 设置20帧作为背景模型的帧
    # KNN背景分割器
    bs = cv2.createBackgroundSubtractorKNN()

    # MOG背景分割器
    # bs = cv2.bgsegm.createBackgroundSubtractorMOG(history = history)
    # bs.setHistory(history)

    # GMG背景分割器
    # bs = cv2.bgsegm.createBackgroundSubtractorGMG(initializationFrames = history)
    # 创建主窗口显示，设置行人字典和firstFrame标志，该标志能使得背景分割器利用这些帧构造历史
    cv2.namedWindow("surveillance")
    pedestrians = {}
    firstFrame = True
    frames = 0
    fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    out = cv2.VideoWriter('../static/output/output.mp4', fourcc, 20.0, (640, 480))
    r = redis.Redis(host='47.98.228.176', port=6379, password='xqQsqDeNAMuSEwu', decode_responses=True, charset='UTF-8',
                    encoding='UTF-8')
    while True:
        # print(" -------------------- FRAME %d --------------------" % frames)
        grabbed, frame = camera.read()
        if grabbed is False:
            print("failed to grab frame.")
            break
        ga, gb, gc, gd = [660, 10, 700, 540]
        cv2.rectangle(frame, (ga, gb), (gc, gd), (255, 255, 0), 1)

        fgmask = bs.apply(frame)

        # 这只是为了让背景分割器建立一些历史
        if frames < history:
            frames += 1
            continue

        # 处理帧，通过前景掩模采用膨胀和腐蚀的方法来识别斑点和周围边框
        th = cv2.threshold(fgmask.copy(), 127, 255, cv2.THRESH_BINARY)[1]  # 将图像上的像素点的灰度值设置为0或255
        th = cv2.erode(th, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (3, 3)), iterations=2)
        dilated = cv2.dilate(th, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (8, 3)), iterations=2)
        contours = cv2.findContours(dilated, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)[0]

        # 对轮廓设置最小区域，以便能对检测进行降噪
        counter = 0
        for c in contours:
            if cv2.contourArea(c) > 500:
                (x, y, w, h) = cv2.boundingRect(c)
                # print(x, y)
                cv2.putText(frame, str(counter), (x, y), font, 0.6, (0, 255, 0), 1, cv2.LINE_AA)
                cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 1)
                center_x, center_y = [x + (w / 2), y + (h / 2)]
                cv2.circle(frame, (int(center_x), int(center_y)), 5, (255, 255, 255), -1)
                if ga < x < gc and gb < y < gd:
                    if (ga - 5 < center_x < ga + 5 and gb - 5 < center_y < gd + 5) or (
                            gc - 5 < center_x < gc + 5 and gb - 5 < center_y < gd + 5) or (
                            gb - 5 < center_y < gb + 5 and ga - 5 < center_x < gc + 5) or (
                            gd - 5 < center_y < gd + 5 and ga - 5 < center_x < gc + 5):
                        people_num += 1
                        print("人流量" + str(people_num))
                    # 只在第一帧中的行人的每个轮廓进行实例化
                if firstFrame is True:
                    pedestrians[counter] = Pedestrian(counter, frame, (x, y, w, h))
                counter += 1

        firstFrame = False  # 表示不会跟踪更多的行人，而是跟踪已有的行人
        frames += 1
        endTime = time.perf_counter()
        sqlEndTime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
        global startTime, sqlStartTime
        if endTime - startTime > 60:
            insertSQL(people_num, sqlEndTime)
            startTime = time.perf_counter()
            sqlStartTime = time.strftime('%Y-%m-%d %H:%M:%S', time.localtime())
            people_num = 0
        cv2.imshow("surveillance", frame)  # 窗口显示结果
        out.write(frame)
        redis_stop = r.get(redisKey)
        print(redis_stop)
        if redis_stop == '1':
            print("总计用时:" + str((endTime - startTime)) + "秒")
            break
        if cv2.waitKey(110) & 0xff == 27:
            print("总计用时:" + str((endTime - startTime)) + "秒")
            break
    cursor.close()
    conn.close()
    out.release()
    camera.release()


if __name__ == "__main__":
    main()
