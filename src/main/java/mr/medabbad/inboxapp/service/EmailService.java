package mr.medabbad.inboxapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import mr.medabbad.inboxapp.repository.EmailRepository;
import mr.medabbad.inboxapp.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import mr.medabbad.inboxapp.model.EmailsList;
import mr.medabbad.inboxapp.model.EmailsListPrimaryKey;
import mr.medabbad.inboxapp.repository.EmailsListRepository;
import mr.medabbad.inboxapp.repository.UnreadEmailStatsRepository;

@Service
public class EmailService {

    @Autowired
    private EmailsListRepository emailsListRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private UnreadEmailStatsRepository unreadEmailStatsRepository;

    public void sendEmail(String fromUserId, String toUserIds, String subject, String body) {

        UUID timeUuid = Uuids.timeBased();

        List<String> toUserIdList = Arrays.asList(toUserIds.split(",")).stream()
                .map(id -> StringUtils.trimWhitespace(id))
                .filter(id -> StringUtils.hasText(id))
                .collect(Collectors.toList());
        // Add to sent items of sender
        EmailsList sentItemEntry = prepareEmailsListEntry("Sent", fromUserId, fromUserId, toUserIdList, subject,
                timeUuid);
        sentItemEntry.setRead(true);
        emailsListRepository.save(sentItemEntry);
        // Add to inbox of each reciever
        toUserIdList.stream().forEach(toUserId -> {
            EmailsList inboxEntry = prepareEmailsListEntry("Inbox", toUserId, fromUserId, toUserIdList, subject,
                    timeUuid);
            inboxEntry.setRead(false);
            emailsListRepository.save(inboxEntry);
            unreadEmailStatsRepository.incrementUnreadCounter(toUserId, "Inbox");
        });

        // Save email entity
        Email email = Email.builder()
                .id(timeUuid)
                .from(fromUserId)
                .to(toUserIdList)
                .subject(subject)
                .body(body)
                .build();
        emailRepository.save(email);

    }

    private EmailsList prepareEmailsListEntry(String folderName, String forUser, String fromUserId,
            List<String> toUserIds, String subject, UUID timeUuid) {

        EmailsListPrimaryKey key = new EmailsListPrimaryKey();
        key.setLabel(folderName);
        key.setUserId(forUser);
        key.setTimeId(timeUuid);
        EmailsList emailsListEntry = new EmailsList();
        emailsListEntry.setId(key);
        emailsListEntry.setFrom(fromUserId);
        emailsListEntry.setTo(toUserIds);
        emailsListEntry.setSubject(subject);
        return emailsListEntry;
    }

}
