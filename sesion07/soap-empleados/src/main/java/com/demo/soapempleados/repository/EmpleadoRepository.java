package com.demo.soapempleados.repository;

import com.demo.soapempleados.model.Empleado;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class EmpleadoRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmpleadoRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<Empleado> listar(){
        String sql = """
            SELECT id, dni, nombres, apellidos, direccion, email, celular, area
            FROM empleados
            ORDER BY id
        """;
        return jdbcTemplate.query( sql, (rs, rowNum) -> new Empleado(
                rs.getLong("id"),
                rs.getString("dni"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("direccion"),
                rs.getString("email"),
                rs.getString("celular"),
                rs.getString("area")
        ));
    }


    public Empleado insertar(Empleado empleado) {
        String sql = """
                INSERT INTO empleados (dni, nombres, apellidos, direccion, email, celular, area)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, empleado.getDni());
            ps.setString(2, empleado.getNombres());
            ps.setString(3, empleado.getApellidos());
            ps.setString(4, empleado.getDireccion());
            ps.setString(5, empleado.getEmail());
            ps.setString(6, empleado.getCelular());
            ps.setString(7, empleado.getArea());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) empleado.setId(key.longValue());

        return empleado;
    }

}
