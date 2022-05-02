package com.example.elancer.common.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkAssessment {
    private int expertise;
    private int scheduleAdherence;
    private int initiative;
    private int communication;
    private int reEmploymentIntention;

    private int totalRawScore;
    private double totalActiveScore;

    public WorkAssessment(int expertise, int scheduleAdherence, int initiative, int communication, int reEmploymentIntention) {
        this.expertise = expertise;
        this.scheduleAdherence = scheduleAdherence;
        this.initiative = initiative;
        this.communication = communication;
        this.reEmploymentIntention = reEmploymentIntention;
    }

    public void estimatedFreelancerWorkAbility(int expertise, int scheduleAdherence, int initiative, int communication, int reEmploymentIntention) {
        this.expertise = expertise;
        this.scheduleAdherence = scheduleAdherence;
        this.initiative = initiative;
        this.communication = communication;
        this.reEmploymentIntention = reEmploymentIntention;
        calculateScore(expertise + scheduleAdherence + initiative + communication + reEmploymentIntention);
    }

    private void calculateScore(int totalSum) {
        this.totalRawScore += totalSum;
        this.totalActiveScore = (double) ((totalRawScore / 5) / 2);
    }

}