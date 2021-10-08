package com.lojafran.Loja_Franciel.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDouble implements Converter<String, Double> {

    @Override
    public Double convert(String source) {
        source = source.trim();
        return source.length() > 0 ? Double.parseDouble(source.replace(".", "").replace(",", ".")) : 0.;
    }
}
