package com.api.rest.service;


import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repositroy.EmpleadoRerpository;
import com.api.rest.service.impl.EmpleadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

;

@ExtendWith(MockitoExtension.class)
//para indicarle que aparte que vamos a trabajar con mockito
// al cargar algunos extension de mockito tambien se dependen de junit
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRerpository empleadoRerpository;


    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleadoPrimero;

    @BeforeEach
    void setUp() {
        empleadoPrimero = Empleado.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("123@gmail.com")
                .build();

    }

    @DisplayName("Test para guardad empleado ")
    @Test
    void testGuadrdarEmpleado() {
        //given
        //dado

        //hay 2 posibilidades:
        given(empleadoRerpository.findById(1L)).willReturn(Optional.empty()); // si el empleado no existe
        given(empleadoRerpository.save(empleadoPrimero)).willReturn(empleadoPrimero); // si el empleado existe

        //cuando
        //when

        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleadoPrimero);

        //entonces
        //then

        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("est para guardad empleado con throw exception")
    @Test
    void testGuardarEmpleadosConThrowException() {
        //given
        //dado


        given(empleadoRerpository.findById(empleadoPrimero.getId())).willReturn(Optional.of(empleadoPrimero)); // si existe rotorna este empleado
        // entonces estamos intentando guardar un empleado que ya existe

        //cuando
        //when

        assertThrows(ResourceNotFoundException.class, () -> empleadoService.saveEmpleado(empleadoPrimero));
        // en este caso vamos a lanzar una excepcion de tipo ResourceNotFoundException en el metodo saveEmpleado

        //entonces
        //then

        Mockito.verify(empleadoRerpository, never()).save(empleadoPrimero);
        // ahora vamos a verificar que el metodo save de empleadoRerpository nunca se llama
    }

    @DisplayName("Test para listar a los emppleados")
    @Test
    void testListarEmpleados() {
        //given
        //dado
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("Juan")
                .apellido("Perez")
                .email("perezz@gmail.com")
                .build();


        given(empleadoRerpository.findAll()).willReturn(List.of(empleadoPrimero, empleado1));
        //cuando
        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();


        //entonces
        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

     @DisplayName("Test para obtener empleado por id")
    @Test
    void testObtenerEmpleadoById() {
         //given
         //dado
         given(empleadoRerpository.findById(1L)).willReturn(Optional.of(empleadoPrimero));
         //cuando
         //when
         Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleadoPrimero.getId()).get();

         //entonces
         //then
         assertThat(empleadoGuardado).isNotNull();
         assertThat(empleadoGuardado.getId()).isEqualTo(empleadoPrimero.getId());

     }

}
