package br.univali.m3_bernardobraga;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class consumirJSON {

    public static List<dados> JsonDados(String conteudo) {
        try{
            List<dados> comidaList = new ArrayList<>();
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;

            jsonArray = new JSONArray(conteudo);

            for ( int i = 0;i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);

                dados dado = new dados();

                dado.setLatitude(jsonObject.getString("latitude"));
                dado.setLongitude(jsonObject.getString("longitude"));
                dado.setBase64(jsonObject.getString("base64"));

                comidaList.add(dado);


            }
            return comidaList;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }






}
