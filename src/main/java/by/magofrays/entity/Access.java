package by.magofrays.entity;

public enum Access {
    ADD_USER,
    RENAME_FAMILY,
    REMOVE_USER,
    CREATE_ROLE,
    GENERATE_INVITE_LINK,
    SHOW_MEMBERS,
    CREATE_TASK,
    ASSIGN_TASK;

    public String getAuthority() {
        return this.name();
    }
}
