
package interface_lib;

import java.security.MessageDigest;

/*************************************************
 * md5 绫诲疄鐜颁簡RSA Data Security, Inc.鍦ㄦ彁浜ょ粰IETF 鐨凴FC1321涓殑MD5 message-digest 绠楁硶銆�
 *************************************************/

public class MD5Util {
    /*
     * 涓嬮潰杩欎簺S11-S44瀹為檯涓婃槸涓�釜4*4鐨勭煩闃碉紝鍦ㄥ師濮嬬殑C瀹炵幇涓槸鐢�define 瀹炵幇鐨勶紝 杩欓噷鎶婂畠浠疄鐜版垚涓簊tatic
     * final鏄〃绀轰簡鍙锛屽垏鑳藉湪鍚屼竴涓繘绋嬬┖闂村唴鐨勫涓�Instance闂村叡浜�
     */
    static final int S11 = 7;

    static final int S12 = 12;

    static final int S13 = 17;

    static final int S14 = 22;

    static final int S21 = 5;

    static final int S22 = 9;

    static final int S23 = 14;

    static final int S24 = 20;

    static final int S31 = 4;

    static final int S32 = 11;

    static final int S33 = 16;

    static final int S34 = 23;

    static final int S41 = 6;

    static final int S42 = 10;

    static final int S43 = 15;

    static final int S44 = 21;

    static final byte[] PADDING = {
            -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0
    };

    /*
     * 涓嬮潰鐨勪笁涓垚鍛樻槸MD5璁＄畻杩囩▼涓敤鍒扮殑3涓牳蹇冩暟鎹紝鍦ㄥ師濮嬬殑C瀹炵幇涓�琚畾涔夊埌MD5_CTX缁撴瀯涓�
     */
    private long[] state = new long[4]; // state (ABCD)

    private long[] count = new long[2]; // number of bits, modulo 2^64 (lsb

    // first)

    private byte[] buffer = new byte[64]; // input buffer

    /**
     * digestHexStr鏄疢D5鐨勫敮涓�竴涓叕鍏辨垚鍛橈紝鏄渶鏂颁竴娆¤绠楃粨鏋滅殑 銆�16杩涘埗ASCII琛ㄧず.
     */
    public String digestHexStr;

    /*
     * digest,鏄渶鏂颁竴娆¤绠楃粨鏋滅殑2杩涘埗鍐呴儴琛ㄧず锛岃〃绀�28bit鐨凪D5鍊�
     */
    private byte[] digest = new byte[16];

    private static char[] Digit = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * b2iu鏄垜鍐欑殑涓�釜鎶奲yte鎸夌収涓嶈�铏戞璐熷彿鐨勫師鍒欑殑锛傚崌浣嶏紓绋嬪簭锛屽洜涓簀ava娌℃湁unsigned杩愮畻
     */
    public static long b2iu(byte b) {
        return b < 0 ? b & 0x7F + 128 : b;
    }

    /**
     * byteHEX()锛岀敤鏉ユ妸涓�釜byte绫诲瀷鐨勬暟杞崲鎴愬崄鍏繘鍒剁殑ASCII琛ㄧず锛�
     * 鍥犱负java涓殑byte鐨則oString鏃犳硶瀹炵幇杩欎竴鐐癸紝鎴戜滑鍙堟病鏈塁璇█涓殑 sprintf(outbuf,"%02X",ib)
     */
    public static String byteHEX(byte ib) {
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(Digit[(b[i] & 0xf0) >>> 4]);
            sb.append(Digit[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    // 杩欐槸MD5杩欎釜绫荤殑鏍囧噯鏋勯�鍑芥暟锛孞avaBean瑕佹眰鏈変竴涓猵ublic鐨勫苟涓旀病鏈夊弬鏁扮殑鏋勯�鍑芥暟
    public MD5Util() {
        md5Init();
    }

    /*
     * F, G, H ,I 鏄�涓熀鏈殑MD5鍑芥暟锛屽湪鍘熷鐨凪D5鐨凜瀹炵幇涓紝鐢变簬瀹冧滑鏄�
     * 绠�崟鐨勪綅杩愮畻锛屽彲鑳藉嚭浜庢晥鐜囩殑鑰冭檻鎶婂畠浠疄鐜版垚浜嗗畯锛屽湪java涓紝鎴戜滑鎶婂畠浠�銆��瀹炵幇鎴愪簡private鏂规硶锛屽悕瀛椾繚鎸佷簡鍘熸潵C涓殑銆�
     */

    /*
     * Decode鎶奲yte鏁扮粍鎸夐『搴忓悎鎴愭垚long鏁扮粍锛屽洜涓簀ava鐨刲ong绫诲瀷鏄�4bit鐨勶紝
     * 鍙悎鎴愪綆32bit锛岄珮32bit娓呴浂锛屼互閫傚簲鍘熷C瀹炵幇鐨勭敤閫�
     */
    private void Decode(long[] output, byte[] input, int len) {
        int i, j;

        for (i = 0, j = 0; j < len; i++, j += 4)
            output[i] = b2iu(input[j]) | (b2iu(input[j + 1]) << 8) | (b2iu(input[j + 2]) << 16)
                    | (b2iu(input[j + 3]) << 24);

        return;
    }

    /*
     * Encode鎶妉ong鏁扮粍鎸夐『搴忔媶鎴恇yte鏁扮粍锛屽洜涓簀ava鐨刲ong绫诲瀷鏄�4bit鐨勶紝 鍙媶浣�2bit锛屼互閫傚簲鍘熷C瀹炵幇鐨勭敤閫�
     */
    private void Encode(byte[] output, long[] input, int len) {
        int i, j;

        for (i = 0, j = 0; j < len; i++, j += 4) {
            output[j] = (byte) (input[i] & 0xffL);
            output[j + 1] = (byte) ((input[i] >>> 8) & 0xffL);
            output[j + 2] = (byte) ((input[i] >>> 16) & 0xffL);
            output[j + 3] = (byte) ((input[i] >>> 24) & 0xffL);
        }
    }

    private long F(long x, long y, long z) {
        return (x & y) | ((~x) & z);

    }

    private long FF(long a, long b, long c, long d, long x, long s, long ac) {
        a += F(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    /*
     * FF,GG,HH鍜孖I灏嗚皟鐢‵,G,H,I杩涜杩戜竴姝ュ彉鎹�FF, GG, HH, and II transformations for
     * rounds 1, 2, 3, and 4. Rotation is separate from addition to prevent
     * recomputation.
     */

    private long G(long x, long y, long z) {
        return (x & z) | (y & (~z));

    }

    public byte[] getMD5ofBytes(String inbuf) {
        md5Init();
        md5Update(inbuf.getBytes(), inbuf.length());
        md5Final();
        return digest;
    }

    /**
     * getMD5ofStr鏄被MD5鏈�富瑕佺殑鍏叡鏂规硶锛屽叆鍙ｅ弬鏁版槸浣犳兂瑕佽繘琛孧D5鍙樻崲鐨勫瓧绗︿覆
     * 杩斿洖鐨勬槸鍙樻崲瀹岀殑缁撴灉锛岃繖涓粨鏋滄槸浠庡叕鍏辨垚鍛榙igestHexStr鍙栧緱鐨勶紟
     */
    public  String getMD5ofStr(String inbuf) {
        md5Init();
        md5Update(inbuf.getBytes(), inbuf.length());
        md5Final();
        digestHexStr = "";
        for (int i = 0; i < 16; i++) {
            digestHexStr += byteHEX(digest[i]);
        }
        return digestHexStr.toLowerCase();
    }

    public String getMD5ofStr(String str, String encode) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(encode));
            return toHexString(messageDigest.digest());
        } catch (Exception e) {
        }
        return null;
    }

    public String getMD5StrOfBytes(byte[] bytes) {
        md5Init();
        md5Update(bytes, bytes.length);
        md5Final();
        digestHexStr = "";
        for (int i = 0; i < 16; i++) {
            digestHexStr += byteHEX(digest[i]);
        }
        return digestHexStr.toLowerCase();
    }

    private long GG(long a, long b, long c, long d, long x, long s, long ac) {
        a += G(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    /*
     * md5Memcpy鏄竴涓唴閮ㄤ娇鐢ㄧ殑byte鏁扮粍鐨勫潡鎷疯礉鍑芥暟锛屼粠input鐨刬npos寮�鎶妉en闀垮害鐨�銆��銆��銆�
     * 瀛楄妭鎷疯礉鍒皁utput鐨刼utpos浣嶇疆寮�
     */

    private long H(long x, long y, long z) {
        return x ^ y ^ z;
    }

    private long HH(long a, long b, long c, long d, long x, long s, long ac) {
        a += H(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    private long I(long x, long y, long z) {
        return y ^ (x | (~z));
    }

    private long II(long a, long b, long c, long d, long x, long s, long ac) {
        a += I(b, c, d) + x + ac;
        a = ((int) a << s) | ((int) a >>> (32 - s));
        a += b;
        return a;
    }

    /*
     * md5Final鏁寸悊鍜屽～鍐欒緭鍑虹粨鏋�
     */
    private void md5Final() {
        byte[] bits = new byte[8];
        int index, padLen;

        // /* Save number of bits */
        Encode(bits, count, 8);

        // /* Pad out to 56 mod 64.
        index = (int) (count[0] >>> 3) & 0x3f;
        padLen = (index < 56) ? (56 - index) : (120 - index);
        md5Update(PADDING, padLen);

        // /* Append length (before padding) */
        md5Update(bits, 8);

        // /* Store state in digest */
        Encode(digest, state, 16);

    }

    /* md5Init鏄竴涓垵濮嬪寲鍑芥暟锛屽垵濮嬪寲鏍稿績鍙橀噺锛岃鍏ユ爣鍑嗙殑骞绘暟 */
    private void md5Init() {
        count[0] = 0L;
        count[1] = 0L;
        // /* Load magic initialization constants.

        state[0] = 0x67452301L;
        state[1] = 0xefcdab89L;
        state[2] = 0x98badcfeL;
        state[3] = 0x10325476L;

        return;
    }

    private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len) {
        int i;

        for (i = 0; i < len; i++)
            output[outpos + i] = input[inpos + i];
    }

    /*
     * md5Transform鏄疢D5鏍稿績鍙樻崲绋嬪簭锛屾湁md5Update璋冪敤锛宐lock鏄垎鍧楃殑鍘熷瀛楄妭
     */
    private void md5Transform(byte block[]) {
        long a = state[0], b = state[1], c = state[2], d = state[3];
        long[] x = new long[16];

        Decode(x, block, 64);

        /* Round 1 */
        a = FF(a, b, c, d, x[0], S11, 0xd76aa478L); /* 1 */
        d = FF(d, a, b, c, x[1], S12, 0xe8c7b756L); /* 2 */
        c = FF(c, d, a, b, x[2], S13, 0x242070dbL); /* 3 */
        b = FF(b, c, d, a, x[3], S14, 0xc1bdceeeL); /* 4 */
        a = FF(a, b, c, d, x[4], S11, 0xf57c0fafL); /* 5 */
        d = FF(d, a, b, c, x[5], S12, 0x4787c62aL); /* 6 */
        c = FF(c, d, a, b, x[6], S13, 0xa8304613L); /* 7 */
        b = FF(b, c, d, a, x[7], S14, 0xfd469501L); /* 8 */
        a = FF(a, b, c, d, x[8], S11, 0x698098d8L); /* 9 */
        d = FF(d, a, b, c, x[9], S12, 0x8b44f7afL); /* 10 */
        c = FF(c, d, a, b, x[10], S13, 0xffff5bb1L); /* 11 */
        b = FF(b, c, d, a, x[11], S14, 0x895cd7beL); /* 12 */
        a = FF(a, b, c, d, x[12], S11, 0x6b901122L); /* 13 */
        d = FF(d, a, b, c, x[13], S12, 0xfd987193L); /* 14 */
        c = FF(c, d, a, b, x[14], S13, 0xa679438eL); /* 15 */
        b = FF(b, c, d, a, x[15], S14, 0x49b40821L); /* 16 */

        /* Round 2 */
        a = GG(a, b, c, d, x[1], S21, 0xf61e2562L); /* 17 */
        d = GG(d, a, b, c, x[6], S22, 0xc040b340L); /* 18 */
        c = GG(c, d, a, b, x[11], S23, 0x265e5a51L); /* 19 */
        b = GG(b, c, d, a, x[0], S24, 0xe9b6c7aaL); /* 20 */
        a = GG(a, b, c, d, x[5], S21, 0xd62f105dL); /* 21 */
        d = GG(d, a, b, c, x[10], S22, 0x2441453L); /* 22 */
        c = GG(c, d, a, b, x[15], S23, 0xd8a1e681L); /* 23 */
        b = GG(b, c, d, a, x[4], S24, 0xe7d3fbc8L); /* 24 */
        a = GG(a, b, c, d, x[9], S21, 0x21e1cde6L); /* 25 */
        d = GG(d, a, b, c, x[14], S22, 0xc33707d6L); /* 26 */
        c = GG(c, d, a, b, x[3], S23, 0xf4d50d87L); /* 27 */
        b = GG(b, c, d, a, x[8], S24, 0x455a14edL); /* 28 */
        a = GG(a, b, c, d, x[13], S21, 0xa9e3e905L); /* 29 */
        d = GG(d, a, b, c, x[2], S22, 0xfcefa3f8L); /* 30 */
        c = GG(c, d, a, b, x[7], S23, 0x676f02d9L); /* 31 */
        b = GG(b, c, d, a, x[12], S24, 0x8d2a4c8aL); /* 32 */

        /* Round 3 */
        a = HH(a, b, c, d, x[5], S31, 0xfffa3942L); /* 33 */
        d = HH(d, a, b, c, x[8], S32, 0x8771f681L); /* 34 */
        c = HH(c, d, a, b, x[11], S33, 0x6d9d6122L); /* 35 */
        b = HH(b, c, d, a, x[14], S34, 0xfde5380cL); /* 36 */
        a = HH(a, b, c, d, x[1], S31, 0xa4beea44L); /* 37 */
        d = HH(d, a, b, c, x[4], S32, 0x4bdecfa9L); /* 38 */
        c = HH(c, d, a, b, x[7], S33, 0xf6bb4b60L); /* 39 */
        b = HH(b, c, d, a, x[10], S34, 0xbebfbc70L); /* 40 */
        a = HH(a, b, c, d, x[13], S31, 0x289b7ec6L); /* 41 */
        d = HH(d, a, b, c, x[0], S32, 0xeaa127faL); /* 42 */
        c = HH(c, d, a, b, x[3], S33, 0xd4ef3085L); /* 43 */
        b = HH(b, c, d, a, x[6], S34, 0x4881d05L); /* 44 */
        a = HH(a, b, c, d, x[9], S31, 0xd9d4d039L); /* 45 */
        d = HH(d, a, b, c, x[12], S32, 0xe6db99e5L); /* 46 */
        c = HH(c, d, a, b, x[15], S33, 0x1fa27cf8L); /* 47 */
        b = HH(b, c, d, a, x[2], S34, 0xc4ac5665L); /* 48 */

        /* Round 4 */
        a = II(a, b, c, d, x[0], S41, 0xf4292244L); /* 49 */
        d = II(d, a, b, c, x[7], S42, 0x432aff97L); /* 50 */
        c = II(c, d, a, b, x[14], S43, 0xab9423a7L); /* 51 */
        b = II(b, c, d, a, x[5], S44, 0xfc93a039L); /* 52 */
        a = II(a, b, c, d, x[12], S41, 0x655b59c3L); /* 53 */
        d = II(d, a, b, c, x[3], S42, 0x8f0ccc92L); /* 54 */
        c = II(c, d, a, b, x[10], S43, 0xffeff47dL); /* 55 */
        b = II(b, c, d, a, x[1], S44, 0x85845dd1L); /* 56 */
        a = II(a, b, c, d, x[8], S41, 0x6fa87e4fL); /* 57 */
        d = II(d, a, b, c, x[15], S42, 0xfe2ce6e0L); /* 58 */
        c = II(c, d, a, b, x[6], S43, 0xa3014314L); /* 59 */
        b = II(b, c, d, a, x[13], S44, 0x4e0811a1L); /* 60 */
        a = II(a, b, c, d, x[4], S41, 0xf7537e82L); /* 61 */
        d = II(d, a, b, c, x[11], S42, 0xbd3af235L); /* 62 */
        c = II(c, d, a, b, x[2], S43, 0x2ad7d2bbL); /* 63 */
        b = II(b, c, d, a, x[9], S44, 0xeb86d391L); /* 64 */

        state[0] += a;
        state[1] += b;
        state[2] += c;
        state[3] += d;

    }

    /*
     * md5Update鏄疢D5鐨勪富璁＄畻杩囩▼锛宨nbuf鏄鍙樻崲鐨勫瓧鑺備覆锛宨nputlen鏄暱搴︼紝杩欎釜
     * 鍑芥暟鐢眊etMD5ofStr璋冪敤锛岃皟鐢ㄤ箣鍓嶉渶瑕佽皟鐢╩d5init锛屽洜姝ゆ妸瀹冭璁℃垚private鐨�
     */
    private void md5Update(byte[] inbuf, int inputLen) {

        int i, index, partLen;
        byte[] block = new byte[64];
        index = (int) (count[0] >>> 3) & 0x3F;
        // /* Update number of bits */
        if ((count[0] += (inputLen << 3)) < (inputLen << 3))
            count[1]++;
        count[1] += (inputLen >>> 29);

        partLen = 64 - index;

        // Transform as many times as possible.
        if (inputLen >= partLen) {
            md5Memcpy(buffer, inbuf, index, 0, partLen);
            md5Transform(buffer);

            for (i = partLen; i + 63 < inputLen; i += 64) {

                md5Memcpy(block, inbuf, 0, i, 64);
                md5Transform(block);
            }
            index = 0;

        } else

            i = 0;

        // /* Buffer remaining input */
        md5Memcpy(buffer, inbuf, index, i, inputLen - i);

    }

}