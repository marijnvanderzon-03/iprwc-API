package iprwcAPI.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import iprwcAPI.Models.DBFile;

import java.io.File;
import java.util.Optional;


public interface DBFileRepository extends JpaRepository<DBFile, String> {
    Optional<DBFile> findByFileName(String filename);

}