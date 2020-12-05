package mglobe.africa.io;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mglobe.africa.io");

        noClasses()
            .that()
                .resideInAnyPackage("mglobe.africa.io.service..")
            .or()
                .resideInAnyPackage("mglobe.africa.io.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..mglobe.africa.io.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
