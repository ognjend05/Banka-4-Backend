package rs.banka4.user_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rs.banka4.user_service.controller.docs.ClientApiDocumentation;
import rs.banka4.user_service.domain.user.client.dtos.ClientContactDto;
import rs.banka4.user_service.domain.user.client.dtos.ClientDto;
import rs.banka4.user_service.domain.user.PrivilegesDto;
import rs.banka4.user_service.domain.user.client.dtos.ClientContactRequest;
import rs.banka4.user_service.domain.user.client.dtos.CreateClientDto;
import rs.banka4.user_service.domain.user.client.dtos.UpdateClientDto;
import rs.banka4.user_service.service.abstraction.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController implements ClientApiDocumentation {

    private final ClientService clientService;

    @Override
    @GetMapping("/privileges")
    public ResponseEntity<PrivilegesDto> getPrivileges(Authentication auth) {
        PrivilegesDto privilegesDto = new PrivilegesDto(List.of());
        return ResponseEntity.ok(privilegesDto);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<ClientDto> me(Authentication auth) {
        return clientService.getMe(auth.getCredentials().toString());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable UUID id) {
        return clientService.getClient(String.valueOf(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody @Valid CreateClientDto createClientDto) {
        clientService.createClient(createClientDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable UUID id, @RequestBody @Valid UpdateClientDto updateClientDto) {
        clientService.updateClient(id, updateClientDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<Page<ClientDto>> getClients(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return clientService.getClients(firstName, lastName, email, sortBy, PageRequest.of(page, size));
    }
}
