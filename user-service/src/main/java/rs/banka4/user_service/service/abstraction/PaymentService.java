package rs.banka4.user_service.service.abstraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import rs.banka4.user_service.domain.transaction.dtos.TransactionDto;
import rs.banka4.user_service.domain.transaction.db.PaymentStatus;
import rs.banka4.user_service.domain.transaction.dtos.CreatePaymentDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface PaymentService {
    ResponseEntity<TransactionDto> createPayment(Authentication authentication, CreatePaymentDto createPaymentDto);
    ResponseEntity<TransactionDto> createTransfer(Authentication authentication, CreatePaymentDto createPaymentDto);
    ResponseEntity<Page<TransactionDto>> getAllPaymentsForClient(String token, PaymentStatus paymentStatus, BigDecimal amount, LocalDate paymentDate,  String accountNumber, PageRequest pageRequest);
    ResponseEntity<TransactionDto> getTransactionById(String token, UUID transactionId);
}
