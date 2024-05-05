package org.globaroman.petshopba.service.impl;

import com.ibm.icu.text.Transliterator;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.globaroman.petshopba.service.TransliterationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransliterationServiceImpl implements TransliterationService {

    public static final String CYRILLIC_TO_LATIN = "Ukrainian-Latin/BGN";

    @Override
    public String toLatin(String line) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(line);
    }

    public String getLatinStringLine(String name) {
        String[] lines = name.split("[^\\p{IsAlphabetic}]+");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String line = toLatin(lines[i]).toLowerCase(Locale.ROOT);
            if (i == lines.length - 1) {
                sb.append(line);
                return sb.toString();
            }
            sb.append(line).append("-");
        }

        return "";
    }
}
