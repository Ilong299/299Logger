package dev.ilong299.logger;

import dev.ilong299.logger.search.Search;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.List;

public class SearchQueryBuilder {

    public List<String> senders; public List<String> types; public List<String> queries; public List<String> logFiles;

    // TODO: Command for this with clickable text
    public SearchQueryBuilder() {

    }

    public void call() {
        try {
            Search.searchQuery(senders, types, queries, logFiles);
        } catch (IOException e) {
            Bukkit.getLogger().severe("299Logger search failed.");
        }
    }
}
