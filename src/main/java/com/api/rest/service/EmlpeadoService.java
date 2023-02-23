package com.api.rest.service;

import com.api.rest.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmlpeadoService {

    Empleado saveEmpleado(Empleado empleado);

    List<Empleado> getAllEmpleados();

    Optional<Empleado> getEmpleadoById(Long id);

    Empleado updateEmpleado(Empleado empleado);

    void deleteEmpleado(Long id);

}
