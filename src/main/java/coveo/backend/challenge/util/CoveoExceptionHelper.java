package coveo.backend.challenge.util;

import coveo.backend.challenge.model.CoveoException;

public class CoveoExceptionHelper {
    public static void throwSemaphoreAcquireErrorUpdate(Exception ex) throws CoveoException{
        throw new CoveoException(500, "Could not garantee integrity while database object is updated", "fatal thread error", ex.getMessage());
    }
    public static void throwSemaphoreAcquireErrorGet(Exception ex) throws CoveoException{
        throw new CoveoException(500, "Could not garantee integrity of database object", "fatal thread error", ex.getMessage());
    }
    public static void throwCannotFetchCityData(Exception ex) throws CoveoException{
        throw new CoveoException(500, "Unable to fetch city data from URL", "Unable to load remote TSV file", ex.getMessage());
    }
    public static void throwCityDataParseError(Exception ex) throws CoveoException{
        throw new CoveoException(500, "Was not able to parse TSV line", "Bad data in TSV", ex.getMessage());
    }
    public static void throwLocationParamError(Exception ex) throws CoveoException{
        throw new CoveoException(400, "Invalid Location parameters", "ex: latitude=43.70011&longitude=-79.4163", ex.getMessage());
    }
}
