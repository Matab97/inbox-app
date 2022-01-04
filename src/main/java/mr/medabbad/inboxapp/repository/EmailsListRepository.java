package mr.medabbad.inboxapp.repository;

import java.util.List;

import mr.medabbad.inboxapp.model.EmailsList;
import mr.medabbad.inboxapp.model.EmailsListPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmailsListRepository extends CassandraRepository<EmailsList, EmailsListPrimaryKey>  {
    List<EmailsList> findAllById_UserIdAndId_Label(String userId, String label); 
}
