package org.jeecg.config.vo;

/**
 * @Author 余维华
 * @date: 2023/11/01
 */
public class Elasticsearch {
    private String clusterNodes;
    private boolean checkEnabled;

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public boolean isCheckEnabled() {
        return checkEnabled;
    }

    public void setCheckEnabled(boolean checkEnabled) {
        this.checkEnabled = checkEnabled;
    }
}
