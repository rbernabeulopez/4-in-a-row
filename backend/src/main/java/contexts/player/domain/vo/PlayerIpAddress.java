package contexts.player.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import contexts.player.domain.exception.InvalidValueException;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@EqualsAndHashCode
@ToString
public class PlayerIpAddress {
    @JsonValue
    private final String ipAddress;


    private boolean isValidIp(String ip) {
        try {
            InetAddress.getByName(ip);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public PlayerIpAddress(String ipAddress) {
        if (!isValidIp(ipAddress)) {
            throw new InvalidValueException("Invalid IP address");
        }
        this.ipAddress = ipAddress;
    }
}
