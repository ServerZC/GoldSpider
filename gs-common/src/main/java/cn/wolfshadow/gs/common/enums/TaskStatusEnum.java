package cn.wolfshadow.gs.common.enums;

public enum TaskStatusEnum {
    /**
     * 任务状态：-1，草稿；0，未处理；1，处理中；2，已完成
     */
    DRAFT("-1","草稿"),
    UNDISPOSED("0","未处理"),
    PROCESSING("1","处理中"),
    FINISHED("2","已完成");

    private String status;
    private String describe;

    TaskStatusEnum(String status, String describe){
        this.status = status;
        this.describe = describe;
    }

    public String getStatus() {
        return status;
    }

    public String getDescribe() {
        return describe;
    }

    public boolean valueEquals(String value){
        if (value == null || this.getStatus() == null) return true;
        return value != null && value.equals(this.status);
    }

    public static boolean contains(String str){
        TaskStatusEnum[] values = TaskStatusEnum.values();
        for(TaskStatusEnum statusEnum : values){
            if (statusEnum.valueEquals(str)) return true;
        }
        return false;
    }
}
