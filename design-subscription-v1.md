# Subscription Service – V1 Design

## Problem & Scope

- Manage subscription plans and user subscriptions.
- No payments, trials, or usage-based billing yet.
- Single region, low traffic, single Postgres DB.

## Data Model

### Entities

- Plan
- Subscription

### ER Diagram (text description for now)

- Plan(id PK, name, price_monthly_cents, description, is_active)
- Subscription(id PK, user_id, plan_id FK -> Plan.id,
  status, started_on, canceled_on,
  current_period_start, current_period_end)

## API Endpoints (V1)

- GET /plans
- POST /plans
- POST /subscriptions
- GET /subscriptions/{user_id}
- POST /subscriptions/{id}/cancel

## Key Flows

- Create subscription (sequence)
- Cancel subscription (sequence)

## Non-functional Notes

- Auth stub: treat user_id as a header for now.
- Logs: include request_id, user_id, subscription_id where relevant.
- Future: events to Billing and Notifications.
