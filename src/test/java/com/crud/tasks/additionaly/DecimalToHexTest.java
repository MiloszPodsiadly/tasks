package com.crud.tasks.additionaly;
import com.crud.tasks.additionally.DecimalToHex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DecimalToHexTest {

    @Test
    void shouldConvertDecimalToHex() {
        // given
        int decimal1 = 255;
        int decimal2 = 16;
        int decimal3 = 0;
        int decimal4 = 4096;

        // when
        String hex1 = DecimalToHex.decimalToHex(decimal1);
        String hex2 = DecimalToHex.decimalToHex(decimal2);
        String hex3 = DecimalToHex.decimalToHex(decimal3);
        String hex4 = DecimalToHex.decimalToHex(decimal4);

        // then
        assertEquals("FF", hex1);    // 255 -> FF
        assertEquals("10", hex2);    // 16 -> 10
        assertEquals("0", hex3);     // 0 -> 0
        assertEquals("1000", hex4);  // 4096 -> 1000
    }
}
