package cinema.Repo;

import cinema.configs.CONST;
import cinema.models.SeatCoordinates;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CinemaRepo {
    Set<SeatCoordinates> availableSeats = new LinkedHashSet<>();

    public CinemaRepo(){
        for (int iRow = 1; iRow <= CONST.getnRows(); iRow++) {
            for (int iCol = 1; iCol <= CONST.getnCols(); iCol++) {
                add(new SeatCoordinates(iRow, iCol));
            }
        }
    }

    public int count(){
        return availableSeats.size();
    }

    public boolean delete(SeatCoordinates seatCoordinates) {
        return availableSeats.remove(seatCoordinates);
    }

    public Collection<SeatCoordinates> getAvailableSeats(){
        return Collections.unmodifiableCollection(availableSeats);
    }

    public void add(SeatCoordinates seatCoordinates) {
        availableSeats.add(seatCoordinates);
    }
}
