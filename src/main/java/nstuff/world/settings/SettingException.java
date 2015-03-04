package nstuff.world.settings;

import nstuff.world.GeneratorException;

/**
 * Created by vania_000 on 21.02.2015.
 */
public class SettingException extends GeneratorException {
    public SettingException(String message) {
        super(message);
    }

    public SettingException(Throwable cause) {
        super(cause);
    }
}
