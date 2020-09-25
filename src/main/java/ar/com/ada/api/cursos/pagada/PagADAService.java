package ar.com.ada.api.cursos.pagada;

import org.springframework.stereotype.Service;

import ar.com.ada.api.cursos.entities.Estudiante;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@Service
public class PagADAService {

    public Integer crearDeudor(Estudiante estudiante) {

        Deudor deudor = new Deudor();
        //Mapear los datos del estudiante en el deudor
        deudor.nombre = estudiante.getNombre();
        //...

        ResultadoCreacionDeudor rd = crearDeudor(deudor);

        if (rd.isOk)
            return rd.id;

        return 0;
    }

    public ResultadoCreacionDeudor crearDeudor(Deudor deudor) {
        ResultadoCreacionDeudor resultado = new ResultadoCreacionDeudor();

        JsonNode r;
        HttpResponse<JsonNode> request = Unirest.post("https://pagada.herokuapp.com/api/deudores")
                .header("content-type", "application/json")
                .body(deudor) //AcaPasamos el RequestBody
                .header("api", "831DYEY1811NOMECORTENELSERVICIO").asJson();

        r = request.getBody();

        JSONObject jsonObject = r.getObject();

        resultado.isOk = jsonObject.getBoolean("isOk");
        resultado.id = jsonObject.getInt("id");
        resultado.message = jsonObject.getString("message");

        return resultado;

    }
}
