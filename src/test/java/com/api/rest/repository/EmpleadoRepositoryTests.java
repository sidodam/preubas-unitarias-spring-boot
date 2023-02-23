package com.api.rest.repository;

import com.api.rest.model.Empleado;
import com.api.rest.repositroy.EmpleadoRerpository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // esta anotacion es para clases que tiene anotacion entity  aka empleado , (capa de repsoitorio)
public class EmpleadoRepositoryTests {


    @Autowired
    private EmpleadoRerpository empleadoRerpository;


    private Empleado empleadoPrimero;

    @BeforeEach
    void setUp() {
        empleadoPrimero = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("123@gmail.com")
                .build();

    }

    @DisplayName("Test para guardar empleado")
    @Test
    void testSaveEmpleado() {

        //given
        //dado
        Empleado empleado1 = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@gmail.com")
                .build();
        //cuando
        //when

        Empleado empleadoGuardado = empleadoRerpository.save(empleado1);

        //entonces
        //then
        assertThat(empleadoGuardado.getId()).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);

        //concepto basicos de bdd


    }

    @DisplayName("Test para listar a los emppleados")
    @Test
    void testListarEmpleados() {
        //given
        //dado
        Empleado empleado1 = Empleado.builder()
                .nombre("Juan")
                .apellido("Perez")
                .email("juan@gmail.com")
                .build();
        empleadoRerpository.save(empleado1);
        empleadoRerpository.save(empleadoPrimero);

        //when

        List<Empleado> empleados = empleadoRerpository.findAll();

        //then
        assertThat(empleados.size()).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);

    }

    @DisplayName("Test para obtener un empleado por id")
    @Test
    void testGetEmpleadoById() {
        //given
        //dado

        empleadoRerpository.save(empleadoPrimero);

        //when
        Empleado empleado = empleadoRerpository.findById(empleadoPrimero.getId()).get();

        //then
        assertThat(empleado.getId()).isNotNull();
//        assertThat(empleado.getId()).isEqualTo(empleadoPrimero.getId());


    }


    @DisplayName("Test para actualizar un empleado")
    @Test
    void testUpdateEmpleado() {
        //given
        //dado

        empleadoRerpository.save(empleadoPrimero);

        //when
        Empleado empleadoguardado = empleadoRerpository.findById(empleadoPrimero.getId()).get();
        empleadoguardado.setNombre("Cristian");
        empleadoguardado.setApellido("BocaNegra");
        empleadoguardado.setEmail("asdf@gmai;com");

        Empleado empleadoActualizado = empleadoRerpository.save(empleadoguardado);

        //then
        assertThat(empleadoActualizado.getId()).isNotNull();
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Cristian");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("BocaNegra");
        assertThat(empleadoActualizado.getEmail()).isEqualTo("asdf@gmai;com");

    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testDeleteEmpleado() {
        //given
        //dado
        empleadoRerpository.save(empleadoPrimero);
        //when
        empleadoRerpository.deleteById(empleadoPrimero.getId());
        //then
        assertThat(empleadoRerpository.findById(empleadoPrimero.getId()).isPresent()).isFalse();
    }


}
