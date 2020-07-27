import net.minecraft.util.math.MathHelper;

import java.util.Arrays;
import java.util.Date;
import javax.swing.*;

/**
 * @author WangTingZheng
 * @date 2020/7/20 20:43
 * @features
 */
public class App {

    public static void main(String[] args) {
        for (int i = 0; i < 255; i++) {
            System.out.println("i = " +i + ", i <<4>>4 = "+ (i>>4<<4));
        }
    }
}
