package it.eg.sloth.framework.monitor.model;

import it.eg.sloth.framework.monitor.MonitorStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MonitorMapper {

    public static final MonitorMapper INSTANCE = Mappers.getMapper(MonitorMapper.class);

    public abstract MonitorStatisticsRow monitorStatisticsToPojoRow(MonitorStatistics monitorStatistics);
}
