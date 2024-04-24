package cinema.services;


import cinema.Repo.CinemaRepo;
import cinema.Repo.SoldTicketRepo;
import cinema.configs.CONST;
import cinema.exceptions.WrongTokenException;
import cinema.exceptions.AlreadyPurchaseException;
import cinema.exceptions.SeatCoordinatesOutBoundsException;

import cinema.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CinemaRoomService {
    final CinemaRepo cinemaRepo = new CinemaRepo();
    final SoldTicketRepo soldTicketRepo = new SoldTicketRepo();

    public List<Seat> getAvailableSeats() {
        return cinemaRepo.getAvailableSeats().stream()
                .map(this::addPrice)
                .toList();

    }
    private int getPriceForRow (int row){
        return row <= 4 ? 10 : 8;
    }
    private Seat addPrice(SeatCoordinates seatCoordinates){
        return new Seat(
                seatCoordinates.row(),
                seatCoordinates.column(),
                getPriceForRow(seatCoordinates.row()));
    }


    public SoldTicket purchase(SeatCoordinates seatCoordinates) {
        int iRow = seatCoordinates.row();
        int iColumn = seatCoordinates.column();
        if(     iRow < 1 || iRow > CONST.getnRows() ||
                iColumn < 1 || iColumn > CONST.getnCols()){
            throw new SeatCoordinatesOutBoundsException();
        }
        if (cinemaRepo.delete(seatCoordinates)) {
            var seat = addPrice(seatCoordinates);
            String token = UUID.randomUUID().toString();
            var ticket = new SoldTicket(token, seat);
            soldTicketRepo.add(ticket);
            return ticket;
        }else {
            throw new AlreadyPurchaseException();
        }
    }

    public Seat ticketReturn(String token) {
        synchronized (soldTicketRepo) {
            if (soldTicketRepo.exist(token)) {
                var seat = soldTicketRepo.remove(token);
                cinemaRepo.add(new SeatCoordinates(seat.row(), seat.column()));
                return seat;
            } else {
                throw new WrongTokenException();
            }
        }
    }

    public Stats stats(){
        int income = soldTicketRepo.totalCost();
        int available = cinemaRepo.count();
        int purchased = soldTicketRepo.count();
        return new Stats(income, available, purchased);
    }

}