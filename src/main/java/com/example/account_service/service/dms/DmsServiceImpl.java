package com.example.account_service.service.dms;

import com.example.account_service.exeption.DataPendingException;
import com.example.account_service.model.Pending;
import com.example.account_service.model.dto.payment.PaymentRequestDto;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.repository.pending.PendingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DmsServiceImpl implements DmsService {
    private final PendingRepository pendingRepository;
    private final DmsRequestsProcessing dmsRequestsProcessing;

    @Transactional
    @Async("workerAsync")
    @Override
    public void authorisationRequestForPayment() {
        int batchSize = 20;
        int pageNo = 0;
        Page<Pending> page;
        do {
            PageRequest pageable = PageRequest.of(pageNo, batchSize);
            page = pendingRepository.findByPaymentStatus(PaymentStatus.WAITING, pageable);
            if (!page.isEmpty()) {
                dmsRequestsProcessing.startAuthorisation(page.getContent());
            } else {
                log.info("No pending data available");
            }

            pageNo++;
        } while (page.hasNext());
    }

    @Async("workerAsync")
    @Override
    public void clearingRequestForPayment(PaymentRequestDto paymentRequestDto) {
        Pending pending = pendingRepository.findByNumberTransactionAndAccountId(
                paymentRequestDto.getNumberTransaction(), paymentRequestDto.getAccountId()).orElseThrow(
                () -> {
                    log.error("Clearing pending request for payment failed. Request: {}", paymentRequestDto.getRequestId());
                    return new DataPendingException("Pending not found");
                }
        );
        dmsRequestsProcessing.startClearingCapture(pending);
    }

    @Async("workerAsync")
    @Override
    public void cancelledRequestForPayment(PaymentRequestDto paymentRequestDto) {
        Pending pending = pendingRepository.findByNumberTransactionAndAccountId(
                paymentRequestDto.getNumberTransaction(), paymentRequestDto.getAccountId()).orElseThrow(() -> {
            log.error("Cancelled request for payment failed. Request: {}", paymentRequestDto.getRequestId());
            return new DataPendingException("Cancelled request for payment failed");
        });
        pending.setPaymentStatus(PaymentStatus.CANCELLATION);
    }
}
