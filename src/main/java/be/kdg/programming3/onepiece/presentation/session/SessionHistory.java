package be.kdg.programming3.onepiece.presentation.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class SessionHistory implements Serializable {

    private final List<Visit> visits = new ArrayList<>();

    public void addVisit(String path) {
        visits.add(new Visit(path, LocalDateTime.now()));
    }

    public List<Visit> getVisits() {
        return new ArrayList<>(visits);
    }

    public static class Visit implements Serializable {
        private final String path;
        private final LocalDateTime timestamp;

        public Visit(String path, LocalDateTime timestamp) {
            this.path = path;
            this.timestamp = timestamp;
        }

        public String getPath() { return path; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
