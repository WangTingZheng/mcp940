import net.minecraft.util.math.MathHelper;

/**
 * @author WangTingZheng
 * @date 2020/7/20 20:43
 * @features
 */
public class App {

    public static void main(String[] args) {
        byte[] te = new byte[255];
        for (int i = 0; i < 255; i++) {
            System.out.println("i="+i+" i >> 4 = "+(i>>4)+"| i & 15 = "+(i&15));
        }
    }
}
