package org.pah_monitoring.main.services.auxiliary.indicators.implementations;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.auxiliary.IndicatorCard;
import org.pah_monitoring.main.entities.enums.IndicatorType;
import org.pah_monitoring.main.entities.users.users.Patient;
import org.pah_monitoring.main.services.auxiliary.indicators.interfaces.IndicatorCardService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class IndicatorCardServiceImpl implements IndicatorCardService {

    //private final IndicatorService<Spirometry, SpirometryAddingDto, SpirometryTablesDto, SpirometryGraphicsDto> spirometryService;

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
    public List<IndicatorCard> getAll(Patient patient) {
        return Stream.of(IndicatorType.values())
                .map(this::getCardFor)
                .toList();
    }

    private IndicatorCard getCardFor(IndicatorType indicatorType) {
        return actions.get(indicatorType).get();
    }

    private IndicatorCard spirometry(Patient patient) {
        return buildIndicatorCard(
                "spirometry",
                "Спирометрия",
                "spirometry.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard walkTest(Patient patient) {
        return buildIndicatorCard(
                "walk-test",
                "Т6МХ",
                "walk-test.jpg",
                service.lastExaminationDate(patient));
    }

    private IndicatorCard pulseOximetry(Patient patient) {
        return buildIndicatorCard(
                "pulse-oximetry",
                "Пульсоксиметрия",
                "pulse-oximetry.jpg",
                service.lastExaminationDate(patient));
    }

    private IndicatorCard cough(Patient patient) {
        return buildIndicatorCard(
                "cough",
                "Кашель",
                "cough.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard chestPain(Patient patient) {
        return buildIndicatorCard(
                "chest-pain",
                "Боль в груди",
                "chest-pain.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard fainting(Patient patient) {
        return buildIndicatorCard(
                "fainting",
                "Обморок",
                "fainting.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard physicalChanges(Patient patient) {
        return buildIndicatorCard(
                "physical-changes",
                "Физические изменения",
                "physical-changes.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard ascites(Patient patient) {
        return buildIndicatorCard(
                "ascites",
                "Асцит",
                "ascites.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard overallHealth(Patient patient) {
        return buildIndicatorCard(
                "overall-health",
                "Общее самочувствие",
                "overall-health.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard vertigo(Patient patient) {
        return buildIndicatorCard(
                "vertigo",
                "Головокружение",
                "vertigo.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard pressure(Patient patient) {
        return buildIndicatorCard(
                "pressure",
                "Давление",
                "pressure.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard liquidAndWeight(Patient patient) {
        return buildIndicatorCard(
                "liquid-and-weight",
                "Жидкость/вес",
                "liquid-and-weight.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard functionalClass(Patient patient) {
        return buildIndicatorCard(
                "functional-class",
                "Функциональный класс",
                "functional-class.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard bloodTest(Patient patient) {
        return buildIndicatorCard(
                "blood-test",
                "Развёрнутый анализ крови",
                "blood-test.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard electrocardiography(Patient patient) {
        return buildIndicatorCard(
                "electrocardiography",
                "Электрокардиография",
                "electrocardiography.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard radiography(Patient patient) {
        return buildIndicatorCard(
                "radiography",
                "Рентгенография органов грудной клетки",
                "radiography.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard echocardiography(Patient patient) {
        return buildIndicatorCard(
                "echocardiography",
                "Эхокардиография",
                "echocardiography.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard computedTomography(Patient patient) {
        return buildIndicatorCard(
                "computed-tomography",
                "Компьютерная томография органов грудной клетки",
                "computed-tomography.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard catheterization(Patient patient) {
        return buildIndicatorCard(
                "catheterization",
                "Катетеризация правых отделов сердца",
                "catheterization.jpg",
                service.lastExaminationDate(patient)
        );
    }

    private IndicatorCard buildIndicatorCard(String workingName, String name, String fileName, LocalDateTime date) {
        return IndicatorCard
                .builder()
                .workingName(workingName)
                .name(name)
                .filename(fileName)
                .date(date)
                .build();
    }

}
