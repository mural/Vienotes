# Vienotes

    * Configuration: add on your local.properties file the key api.access.token="<API_TOKEN>"

    * Access Token is re-generated each time that the tasks list are retrieved, username is fixed, API_KEY is on build.gradle at the moment
    * No cache used on local storage to save tasks list, so they are retrieved from the server every time
    * On task Create & Delete to refresh the list, we call the server
    * Task name and note(detail) were considered mandatory     * There is no delete confirmation
