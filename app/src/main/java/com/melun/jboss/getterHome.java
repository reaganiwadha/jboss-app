package com.melun.jboss;

public class getterHome {
    private String name;
    private String language;
    private String desc;
    private String stargazer;
    private String watcher;

    public getterHome(String name, String language, String desc, String stargazer, String watcher) {
        this.name = name;
        this.language = language;
        this.desc = desc;
        this.stargazer = stargazer;
        this.watcher = watcher;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getDesc() {
        return desc;
    }

    public String getStargazer() {
        return stargazer;
    }

    public String getWatcher() {
        return watcher;
    }
}
