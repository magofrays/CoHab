package by.magofrays.entity;

public enum Access {
    ADD_USER,
    RENAME_FAMILY,
    REMOVE_USER,
    CREATE_ROLE,
    GENERATE_INVITE_LINK,
    UPDATE_TASK,
    DELETE_TASK,
    SHOW_MEMBERS,
    SHOW_TASKS,
    CREATE_TASK,
    ASSIGN_TASK;

    public String getAuthority() {
        return this.name();
    }
}
