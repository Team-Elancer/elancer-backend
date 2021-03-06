package com.example.elancer.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositionKind {
    DEVELOPER("개발자"),
    PUBLISHER("퍼블리셔"),
    DESIGNER("디자이너"),
    PLANNER("기획자"),
    CROWD_WORKER("크라우드워커"),
    ETC("기타");

    private String desc;

}
