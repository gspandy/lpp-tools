package lpp.tools.comm;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现描述：ip工具类
 * @author lipanpan
 * @time 2016年12月7日
 */
public abstract class IpUtils {

    /**
     * 获取客户端真实的IP
     * @param request
     * @return
     */
    public String getRealIp(HttpServletRequest request) {
        String ipSplit = request.getHeader("X-Forwarded-For");
        String ip = null;
        if (!StringUtils.isEmpty(ipSplit)) {
            ip = ipSplit.split("\\s*,\\s*")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
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
        for (int i = 3; i >= 0; i--) {
            long temp = Long.parseLong(ipInArray[3 - i]);
            result |= temp << (i * 8);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(transIp2Long("192.168.0.5"));
        System.out.println(contains("192.168.0.1-192.168.10.22", "192.168.0.66"));
    }

}
