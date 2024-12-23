package zw.co.titus.tasky.task;

public class TaskSwaggerConstants {

    public static final String TAG = "Tasks Controller";
    public static final String TAG_DESCRIPTION = "APIs for managing tasks, including CRUD operations and pagination";

    public static final String CREATE_SUMMARY = "Create a new task";
    public static final String CREATE_DESCRIPTION = "Add a new task by providing its details";

    public static final String GET_BY_ID_SUMMARY = "Get task by ID";
    public static final String GET_BY_ID_DESCRIPTION = "Retrieve details of a specific task by its ID";

    public static final String UPDATE_SUMMARY = "Update task details";
    public static final String UPDATE_DESCRIPTION = "Update details of an existing task";

    public static final String DELETE_SUMMARY = "Delete a task";
    public static final String DELETE_DESCRIPTION = "Delete an existing task by its ID";

    public static final String GET_ALL_SUMMARY = "Get all tasks";
    public static final String GET_ALL_DESCRIPTION = "Retrieve a paginated list of all tasks with optional search";

    public static final String TITLE_DESCRIPTION = "Filter by task title. Supports partial matches.";
    public static final String DESCRIPTION_DESCRIPTION = "Filter by task description. Supports partial matches.";
    public static final String DEADLINE_DESCRIPTION = "Filter by exact deadline. Provide in ISO format (e.g., 2024-12-25T12:00:00).";

    public static final String PAGE_NUMBER_DESCRIPTION = "Page number (default is 0)";
    public static final String PAGE_SIZE_DESCRIPTION = "Number of items per page (default is 10)";
    public static final String ID_DESCRIPTION = "ID of the task to retrieve, update, or delete";
}
