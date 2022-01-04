package mr.medabbad.inboxapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mr.medabbad.inboxapp.repository.UnreadEmailStatsRepository;
import mr.medabbad.inboxapp.model.Folder;
import mr.medabbad.inboxapp.model.UnreadEmailStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoldersService {

    @Autowired
    UnreadEmailStatsRepository unreadEmailStatsRepository;

    public List<Folder> init(String userId) {
        var defaultFolders = Arrays.asList(
            new Folder(userId, "Inbox", "blue"),
            new Folder(userId, "Sent", "purple"),
            new Folder(userId, "Important", "red"),
            new Folder(userId, "Done", "green")
        );
        // for (int i = 0; i < defaultFolders.size(); i++) {
        //     defaultFolders.get(i).setCreatedTimeUuid(Uuids.timeBased());
        // }
        return defaultFolders;
    }

    public Map<String, Integer> getUnreadCountsMap(String loginId) {
        List<UnreadEmailStats> unreadStats = unreadEmailStatsRepository.findAllById(loginId);
        Map<String, Integer> folderToUnreadCounts = unreadStats.stream()
                .collect(Collectors.toMap(UnreadEmailStats::getLabel, UnreadEmailStats::getUnreadCount));
        return folderToUnreadCounts;
    }
    
}
