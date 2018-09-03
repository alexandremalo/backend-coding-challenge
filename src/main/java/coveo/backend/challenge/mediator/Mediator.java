package coveo.backend.challenge.mediator;/*
 * Copyright (C) 2001-2016, BCE Inc.
 *
 * This program contains proprietary and confidential information which is
 * protected by copyright. All rights are reserved. No part of this program may
 * be photocopied, reproduced or translated into another language, or disclosed
 * to a third party without the prior written consent of Bell Canada, Inc.
 *
 * Created by alex on 2018-09-03.
 */

import coveo.backend.challenge.model.SuggestionResponse;

public interface Mediator {
    public SuggestionResponse getSuggestions(String query);
}
