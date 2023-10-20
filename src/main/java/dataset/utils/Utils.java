package dataset.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import dataset.models.FootballMatch;
import dataset.models.FootballMatchDao;

public class Utils {

	public static List<FootballMatch> readCSV(String filePath, char separator) throws IOException, CsvException {
		List<FootballMatch> footballMatches = new ArrayList<>();
		try (InputStream input = Utils.class.getResourceAsStream(filePath)) {
			CsvToBean<FootballMatchDao> csvToBean = new CsvToBeanBuilder<FootballMatchDao>(new InputStreamReader(input))
					.withType(FootballMatchDao.class).withSeparator(separator).withIgnoreLeadingWhiteSpace(true)
					.build();
			footballMatches = csvToBean.parse().stream().map(l -> {
				return FootballMatch.convertDaoToMatch(l);
			}).collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e);
		}
		return footballMatches;
	}

}
