package lpp.tools.comm;

/**
 * 实现描述：ip工具类
 * @author lipanpan
 * @time 2016年12月7日
 */
public abstract class IpUtils {

    /**
     * 给定义一串ip列表，判断是否包含指定的ip
     * @param iplist ip列表 192.168.0.1,192.168.0.5 - 192.168.10.22
     * @param ip 指定IP 192.168.0.5
     * @param splitRegex 分隔符,
     * @return
     */
    public static boolean batchContains(String iplist, String ip, String splitRegex) {
        if (StringUtils.isEmpty(iplist) || StringUtils.isEmpty(ip)) {
            return false;
        }
        String[] segmentArray = iplist.split(splitRegex);
        if (segmentArray != null && segmentArray.length > 0) {
            for (String segment : segmentArray) {
                if (contains(segment, ip)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断IP或IP段是否包含指定的ip
     * @param segment 192.168.0.1 - 192.168.10.22
     * @param ip 192.168.0.5
     * @return
     */
    public static boolean contains(String segment, String ip) {
        if (StringUtils.isEmpty(segment) || StringUtils.isEmpty(ip)) {
            return false;
        }
        if (segment.equals(ip)) {
            return true;
        }
        int index = segment.indexOf("-");
        if (index > -1) {
            String[] ips = segment.split("\\s*-\\s*");
            if (ips != null && ips.length == 2) {
                return contains(ips, ip);
            }
        }
        return false;
    }

    /**
     * 判断IP或IP段是否包含指定的ip
     * @param ips [192.168.0.1, 192.168.10.22]
     * @param ip 192.168.0.5
     * @return
     */
    public static boolean contains(String[] ips, String ip) {
        if (ips == null || ips.length != 2 || StringUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("param is invalid.");
        }
        Long compareIp = transIp2Long(ip);
        Long minIp = transIp2Long(ips[0]);
        Long maxIp = transIp2Long(ips[1]);
        if (minIp > maxIp) {
            // 交换大小
            Long temp = maxIp;
            maxIp = minIp;
            minIp = temp;
        }
        return (compareIp >= minIp && compareIp <= maxIp);
    }

    /**
     * 将ip转换为十进制数
     * @param ip
     * @return
     */
    public static Long transIp2Long(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return -1L;
        }
        ip = ip.trim();
        Long result = 0L;
        String[] ipInArray = ip.split("\\.");
        long temp;
        for (int i = 3; i >= 0; i--) {
            temp = Long.parseLong(ipInArray[3 - i]);
            result |= temp << (i * 8);
        }
        return result;
    }

}
