package cinema.controller;


import cinema.configs.CONST;
import cinema.exceptions.WrongPasswordException;
import cinema.models.*;
import cinema.services.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CinemaController {
    final CinemaRoomService cinemaRoomService = new CinemaRoomService();

    @GetMapping("/seats")
    CinemaRoom getAvailableSeats(){
        int nRows = CONST.getnRows();
        int nColumns = CONST.getnCols();
        List<Seat> seats = cinemaRoomService.getAvailableSeats();
        return new CinemaRoom(nRows,nColumns,seats);
    }

    @PostMapping("/purchase")
    SoldTicket purchase(@RequestBody SeatCoordinates seatCoordinates){
        return cinemaRoomService.purchase(seatCoordinates);
    }

    @PostMapping("/return")
    ReturnedTicketResponse ticketReturn(@RequestBody TicketReturnRequest ticketReturnRequest){
        Seat seat = cinemaRoomService.ticketReturn(ticketReturnRequest.token());
        return new ReturnedTicketResponse(seat);
    }

    @GetMapping("/stats")
    Stats getStatistics(@RequestParam Optional<String> password){
        password.filter(CONST.getsecret()::equals)
                .orElseThrow(WrongPasswordException::new);
        return  cinemaRoomService.stats();
    }
}
