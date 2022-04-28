package spd.trello.domain.enums;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_UPDATE("developers:update"),
    DEVELOPERS_WRITE("developers:write");


    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
