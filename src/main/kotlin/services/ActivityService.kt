package org.example.services

import org.example.data.models.Activity
import org.example.data.repositories.ActivityRepository

class ActivityService(private val activityRepository: ActivityRepository) {
    fun createActivity(activity: Activity): Activity? {
        return activityRepository.createActivity(activity)
    }
    fun getUserActivities(userId: Int): List<Activity> {
        return activityRepository.getActivitiesByUserId(userId)
    }
}