package com.jumpplus.tracker.dao;

import com.jumpplus.tracker.model.Progress;

import java.util.List;

public interface ProgressDao {
    public List<Progress> getAllUserTrackers(int u_id);


    public boolean updateProgress(Progress progress);

    public boolean addProgress(Progress progress);

    public List<Progress> getAllProgress();

    public List<Progress> getAveRatings();


}
