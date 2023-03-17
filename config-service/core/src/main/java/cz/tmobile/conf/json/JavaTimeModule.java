package cz.tmobile.conf.json;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.ValueInstantiators;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedClassResolver;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.DurationKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.PeriodKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.YearKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.YearMonthKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZoneIdKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZoneOffsetKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.MonthDaySerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

public final class JavaTimeModule extends SimpleModule
{
    private static final long serialVersionUID = 1L;

    public JavaTimeModule()
    {
        super(PackageVersion.VERSION);

        // First deserializers

        // // Instant variants:
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);

        // // Other deserializers
        addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm:ss")));
        addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        addDeserializer(Year.class, YearDeserializer.INSTANCE);
        addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);

        
        // then serializers:
        addSerializer(Duration.class, DurationSerializer.INSTANCE);
        addSerializer(Instant.class, InstantSerializer.INSTANCE);
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm:ss")));
        addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        addSerializer(Period.class, new ToStringSerializer(Period.class));
        addSerializer(Year.class, YearSerializer.INSTANCE);
        addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);

        /* 27-Jun-2015, tatu: This is the real difference from the old
         *  {@link JSR310Module}: default is to produce ISO-8601 compatible
         *  serialization with timezone offset only, not timezone id.
         *  But this is configurable.
         */
        addSerializer(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);
        
        // note: actual concrete type is `ZoneRegion`, but that's not visible:
        addSerializer(ZoneId.class, new ToStringSerializer(ZoneId.class));

        addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));

        // key serializers
        addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);

        // key deserializers
        addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        addKeyDeserializer(YearMonth.class, YearMonthKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addValueInstantiators(new ValueInstantiators.Base() {
            @Override
            public ValueInstantiator findValueInstantiator(DeserializationConfig config,
                    BeanDescription beanDesc, ValueInstantiator defaultInstantiator)
            {
                JavaType type = beanDesc.getType();
                Class<?> raw = type.getRawClass();

                // 15-May-2015, tatu: In theory not safe, but in practice we do need to do "fuzzy" matching
                // because we will (for now) be getting a subtype, but in future may want to downgrade
                // to the common base type. Even more, serializer may purposefully force use of base type.
                // So... in practice it really should always work, in the end. :)
                if (ZoneId.class.isAssignableFrom(raw)) {
                    // let's assume we should be getting "empty" StdValueInstantiator here:
                    if (defaultInstantiator instanceof StdValueInstantiator) {
                        StdValueInstantiator inst = (StdValueInstantiator) defaultInstantiator;
                        // one further complication: we need ZoneId info, not sub-class
                        AnnotatedClass ac;
                        if (raw == ZoneId.class) {
                            ac = beanDesc.getClassInfo();
                        } else {
                            // we don't need Annotations, so constructing directly is fine here
                            // even if it's not generally recommended
                            ac = AnnotatedClassResolver.resolve(config,
                                    config.constructType(ZoneId.class), config);
                        }
                        if (!inst.canCreateFromString()) {
                            AnnotatedMethod factory = _findFactory(ac, "of", String.class);
                            if (factory != null) {
                                inst.configureFromStringCreator(factory);
                            }
                            // otherwise... should we indicate an error?
                        }
                        // return ZoneIdInstantiator.construct(config, beanDesc, defaultInstantiator);
                    }
                }
                return defaultInstantiator;
            }
        });
    }

    // For
    private AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes)
    {
        final int argCount = argTypes.length;
        next_method: for (AnnotatedMethod method : cls.getFactoryMethods()) {
            if (!name.equals(method.getName())
                    || (method.getParameterCount() != argCount)) {
                continue;
            }
            for (int i = 0; i < argCount; ++i) {
                Class<?> argType = method.getParameter(i).getRawType();
                if (!argType.isAssignableFrom(argTypes[i])) {
                    continue next_method;
                    // TODO: Confirm: This 'continue' was redundant, the condition had no body, so the inner for-loop.
                    //       That makes it completely redundant - that does not make sense, why anyone would write this?
                    //       The explanation would be that the 'continue' is meant as an interruption of the outer loop,
                    //       but that simply does not work like that - the 'continue' needs a label to get to the
                    //       beginning of the outer loop. So how did that work in the past, were there some procedures
                    //       that were fixing this behavior?
                }
            }
            return method;
        }
        return null;
    }
}