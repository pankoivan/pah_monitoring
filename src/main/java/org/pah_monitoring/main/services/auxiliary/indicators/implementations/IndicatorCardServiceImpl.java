package org.pah_monitoring.main.services.auxiliary.indicators.implementations;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class IndicatorCardServiceImpl implements IndicatorCardService {

    private final Map<IndicatorType, Supplier<IndicatorCard>> actions = Map.ofEntries(
            Map.entry(IndicatorType.SPIROMETRY, this::spirometry),
            Map.entry(IndicatorType.WALK_TEST, this::walkTest),
            Map.entry(IndicatorType.PULSE_OXIMETRY, this::pulseOximetry),
            Map.entry(IndicatorType.COUGH, this::cough),
            Map.entry(IndicatorType.CHEST_PAIN, this::chestPain),
            Map.entry(IndicatorType.FAINTING, this::fainting),
            Map.entry(IndicatorType.PHYSICAL_CHANGES, this::physicalChanges),
            Map.entry(IndicatorType.ASCITES, this::ascites),
            Map.entry(IndicatorType.OVERALL_HEALTH, this::overallHealth),
            Map.entry(IndicatorType.VERTIGO, this::vertigo),
            Map.entry(IndicatorType.PRESSURE, this::pressure),
            Map.entry(IndicatorType.LIQUID_AND_WEIGHT, this::liquidAndWeight),
            Map.entry(IndicatorType.FUNCTIONAL_CLASS, this::functionalClass),
            Map.entry(IndicatorType.BLOOD_TEST, this::bloodTest),
            Map.entry(IndicatorType.ELECTROCARDIOGRAPHY, this::electrocardiography),
            Map.entry(IndicatorType.RADIOGRAPHY, this::radiography),
            Map.entry(IndicatorType.ECHOCARDIOGRAPHY, this::echocardiography),
            Map.entry(IndicatorType.COMPUTED_TOMOGRAPHY, this::computedTomography),
            Map.entry(IndicatorType.CATHETERIZATION, this::catheterization)
    );

    @Override
    public List<IndicatorCard> getAll() {
        return Stream.of(IndicatorType.values())
                .map(this::getCardFor)
                .toList();
    }

    private IndicatorCard getCardFor(IndicatorType indicatorType) {
        return actions.get(indicatorType).get();
    }

    private IndicatorCard spirometry() {
        return buildIndicatorCard("spirometry", "Спирометрия", "spirometry.jpg");
    }

    private IndicatorCard walkTest() {
        return buildIndicatorCard("walk-test", "Т6МХ", "walk-test.jpg");
    }

    private IndicatorCard pulseOximetry() {
        return buildIndicatorCard("pulse-oximetry", "Пульсоксиметрия", "pulse-oximetry.jpg");
    }

    private IndicatorCard cough() {
        return buildIndicatorCard("cough", "Кашель", "cough.jpg");
    }

    private IndicatorCard chestPain() {
        return buildIndicatorCard("chest-pain", "Боль в груди", "chest-pain.jpg");
    }

    private IndicatorCard fainting() {
        return buildIndicatorCard("fainting", "Обморок", "fainting.jpg");
    }

    private IndicatorCard physicalChanges() {
        return buildIndicatorCard("physical-changes", "Физические изменения", "physical-changes.jpg");
    }

    private IndicatorCard ascites() {
        return buildIndicatorCard("ascites", "Асцит", "ascites.jpg");
    }

    private IndicatorCard overallHealth() {
        return buildIndicatorCard("overall-health", "Общее самочувствие", "overall-health.jpg");
    }

    private IndicatorCard vertigo() {
        return buildIndicatorCard("vertigo", "Головокружение", "vertigo.jpg");
    }

    private IndicatorCard pressure() {
        return buildIndicatorCard("pressure", "Давление", "pressure.jpg");
    }

    private IndicatorCard liquidAndWeight() {
        return buildIndicatorCard("liquid-and-weight", "Жидкость/вес", "liquid-and-weight.jpg");
    }

    private IndicatorCard functionalClass() {
        return buildIndicatorCard("functional-class", "Функциональный класс", "functional-class.jpg");
    }

    private IndicatorCard bloodTest() {
        return buildIndicatorCard("blood-test", "Развёрнутый анализ крови", "blood-test.jpg");
    }

    private IndicatorCard electrocardiography() {
        return buildIndicatorCard("electrocardiography", "Электрокардиография", "electrocardiography.jpg");
    }

    private IndicatorCard radiography() {
        return buildIndicatorCard("radiography", "Рентгенография органов грудной клетки", "radiography.jpg");
    }

    private IndicatorCard echocardiography() {
        return buildIndicatorCard("echocardiography", "Эхокардиография", "echocardiography.jpg");
    }

    private IndicatorCard computedTomography() {
        return buildIndicatorCard("computed-tomography", "Компьютерная томография органов грудной клетки", "computed-tomography.jpg");
    }

    private IndicatorCard catheterization() {
        return buildIndicatorCard("catheterization", "Катетеризация правых отделов сердца", "catheterization.jpg");
    }

    private IndicatorCard buildIndicatorCard(String workingName, String name, String fileName) {
        return IndicatorCard
                .builder()
                .workingName(workingName)
                .name(name)
                .filename(fileName)
                .build();
    }

}
