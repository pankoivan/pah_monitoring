package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AnalysisType {

    BLOOD_TEST("Развёрнутый анализ крови"),

    ELECTROCARDIOGRAPHY("Электрокардиография"),

    RADIOGRAPHY("Рентгенография органов грудной клетки"),

    ECHOCARDIOGRAPHY("Эхокардиография"),

    COMPUTED_TOMOGRAPHY("Компьютерная томография органов грудной клетки"),

    CATHETERIZATION("Катетеризация правых отделов сердца");

    private final String alias;

}
