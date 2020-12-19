package pl.study.thesis.handler;

import pl.study.thesis.dao.EventDao;

public class RepoHandler {
    private static EventDao eventDao;

    public static EventDao getEventDao() {
        return eventDao;
    }

    public static void setEventDao(EventDao eventDao) {
        RepoHandler.eventDao = eventDao;
    }
}
