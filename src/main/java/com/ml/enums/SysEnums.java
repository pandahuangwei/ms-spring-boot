package com.ml.enums;

/**
 * 系统枚举.
 *
 * @author panda.
 * @since 2017-07-18 01:40.
 */
public interface SysEnums {
    /**
     * 返回结果的状态.
     */
    enum SimpleResEnum {
        Success(1, "成功"), Fail(0, "失败");

        private int key;
        private String value;

        SimpleResEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return this.key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 菜单类型
     */
    enum MenuType {
        CATALOG(0, "目录"), MENU(1, "菜单"), BUTTON(2, "按钮");

        private int key;
        private String value;

        MenuType(int itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public boolean eq(int itemKey) {
            return this.key == itemKey;
        }
    }

    /**
     * 定时任务状态
     */
    enum ScheduleStatus {

        NORMAL(0, "正常"), PAUSE(1, "暂停");

        private int key;
        private String value;

        ScheduleStatus(int itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public boolean eq(int itemKey) {
            return this.key == itemKey;
        }
    }

}
