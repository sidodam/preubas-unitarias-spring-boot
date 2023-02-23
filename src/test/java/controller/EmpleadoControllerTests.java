package controller;


import com.api.rest.controller.EmpleadoController;
import com.api.rest.model.Empleado;
import com.api.rest.service.EmlpeadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@ContextConfiguration(classes = {EmpleadoController.class})
public class EmpleadoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmlpeadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;


    //POST TEST

    @Test
    void testGuardadoEmpleado() throws Exception {

        //given

        Empleado empleado = Empleado.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("perez23@gmail.copm")
                .build();
        given(empleadoService.saveEmpleado(Mockito.any(Empleado.class))).willAnswer(invocationOnMock ->
                        invocationOnMock.getArgument(0)
                // aqui estamos diciendo a empleadoService.saveEmpleado() como actua , que es lo que va a hacer
                // es decir que va a retornar el empleado que le pasamos como argumento
                //not SAVE just return the object for now
        );

        //when

        ResultActions response = mockMvc.perform(post("/api/empleados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleado)));


        // cuando hacemos un post a la url /api/empleados
        // le pasamos un objeto de tipo empleado en formato json
        // y lo guardamos en la variable response
        //then

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(empleado.getNombre())))
                .andExpect(jsonPath("$.apellido", is(empleado.getApellido())))
                .andExpect(jsonPath("$.email", is(empleado.getEmail())));

        //aqui estamos esperando que el objeto que nos devuelve el post
        // tenga los mismos valores que el objeto empleado que le pasamos como argumento

    }


    //GET TEST

    @Test
    void testGetEmpleados() throws Exception {

        //given
        List<Empleado> listaEmpleados = new ArrayList<>();

         listaEmpleados.add(Empleado.builder()
                .id(1L)
                .nombre("Juan")
                .apellido("Perez")
                .email("aasd@gmail.com")
                .build());

        listaEmpleados.add(Empleado.builder()
                .id(2L)
                .nombre("Juan")
                .apellido("Perez")
                .email("asdasd@asfd.com")
                .build());

     listaEmpleados.add(Empleado.builder()
                .id(3L)
                .nombre("Juan")
                .apellido("Perez")
                .email("asd@asdc.com")
                .build());

     given(empleadoService.getAllEmpleados()).willReturn(listaEmpleados);

            //when

            ResultActions response = mockMvc.perform(get("/api/empleados"));
            // aqui estamos haciendo un get a la url /api/empleados
        // y lo guardamos en la variable response


            //then

        response.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.size()", is(listaEmpleados.size())));
        //aqui estamos esperando que el objeto que nos devuelve el get que
        // su size sea igual al size de la lista de empleados

    }

}
