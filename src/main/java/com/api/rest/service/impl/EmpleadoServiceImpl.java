package com.api.rest.service.impl;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repositroy.EmpleadoRerpository;
import com.api.rest.service.EmlpeadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmpleadoServiceImpl implements EmlpeadoService {

    @Autowired
    private EmpleadoRerpository empleadoRerpository;

    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado> empleadoGuardado = empleadoRerpository.findById(empleado.getId());
        if (empleadoGuardado.isPresent()) {
            throw new ResourceNotFoundException("No se encontro el empleado con id: " + empleado.getId());
        } else {
            return empleadoRerpository.save(empleado);
        }
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRerpository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRerpository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleado) {
        return empleadoRerpository.save(empleado);
    }

    @Override
    public void deleteEmpleado(Long id) {

        empleadoRerpository.deleteById(id);

    }
}
